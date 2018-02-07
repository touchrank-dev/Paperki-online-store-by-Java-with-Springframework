package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Brand;
import com.kushnir.paperki.model.util.Transliterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Repository("brandDao")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BrandDaoImpl implements BrandDao {

    private static final Logger LOGGER = LogManager.getLogger(BrandDaoImpl.class);

    private static final String P_ID = "id";
    private static final String P_PAP_ID = "papId";
    private static final String P_NAME = "name";
    private static final String P_T_MAME = "tName";
    private static final String P_ICON = "icon";
    private static final String P_SHORT_DESCRIPTION = "shortDescription";
    private static final String P_FULL_DESCRIPTION = "fullDescription";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${brand.image.prefix}")
    private String iconPrefix;

    /* CSV */
    @Value("${csv.delimiter}")
    private char delimiter;

    @Value("${csv.escape}")
    private char escape;

    @Value("${path.csv.files}")
    private String csvFilesPath;

    @Value("${csv.file.brands}")
    private String csvFileBrands;

    /* SQL scripts */
    @Value("${brand.getAll}")
    private String getAllSqlQuery;

    @Value("${brand.update}")
    private String updateBrandSqlQuery;

    @Value("${brand.batch.add}")
    private String addSqlQuery;

    @Override
    public HashMap<Integer, Brand> getAll() {
        LOGGER.debug("getAll() >>>");
        HashMap<Integer, Brand> brands =
                namedParameterJdbcTemplate.query(getAllSqlQuery, new BrandsResultSetExtractor());
        return brands;
    }

    @Override
    public HashMap<Integer, Brand> getFromCSV(StringBuilder sb) throws IOException {
        String file = csvFilesPath + csvFileBrands;
        sb.append("Starting retrieve data from CSV file: ").append(file).append('\n')
                .append(">>> PROGRESS ...").append('\n');
        LOGGER.debug("Starting retrieve data from CSV file: {}\n>>> PROGRESS ...", file);

        HashMap<Integer, Brand> brands = new HashMap<>();

        try {
            Iterable<CSVRecord> records =
                    CSVFormat
                            .newFormat(delimiter)
                            .withEscape(escape)
                            .withFirstRecordAsHeader()
                            .parse(new FileReader(file));
            for (CSVRecord record : records) {
                try {
                    Integer papId =     Integer.parseInt(record.get(0));
                    String name =       record.get(1);
                    String latinName =  record.get(2);
                    String tName =      Transliterator.cyr2lat(name);
                    String icon =       generateBrandImageName(papId);

                    Brand brand = new Brand(
                           papId,
                           tName,
                           name,
                           icon
                    );

                    brands.put(papId, brand);
                } catch (Exception e) {
                    sb.append("ERROR >>> row: ").append(record.getRecordNumber())
                            .append(", ").append(e.getMessage()).append('\n');
                    LOGGER.error("ERROR >>> row:{} {}", record.getRecordNumber(), e.getMessage());
                    continue;
                }
            }

        } catch (FileNotFoundException e) {
            sb.append("ERROR >>> File (").append(file).append(") Not Found! >>> ").append(e.getMessage()).append('\n');
            LOGGER.error("ERROR >>> File ({}) Not Found! >>> {}", file, e.getMessage());
            return null;
        }
        sb.append(">>> FINISH").append('\n');
        LOGGER.debug(">>> FINISH");
        return brands;
    }

    private String generateBrandImageName(Integer brandId) {
        String pntStr = String.valueOf(brandId);
        switch(pntStr.length()) {
            case 1: return "00000"+pntStr+iconPrefix;
            case 2: return "0000"+pntStr+iconPrefix;
            case 3: return "000"+pntStr+iconPrefix;
            case 4: return "00"+pntStr+iconPrefix;
            case 5: return "0"+pntStr+iconPrefix;
            default: return pntStr+iconPrefix;
        }
    }

    @Override
    public Brand getBrandByID() {
        return null;
    }

    @Override
    public Brand getBrandByPapID() {
        return null;
    }

    @Override
    public int addBrand(Brand brand) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        parameterSource.addValue(P_PAP_ID, brand.getPapId());
        parameterSource.addValue(P_NAME, brand.getName());
        parameterSource.addValue(P_T_MAME, brand.gettName());
        parameterSource.addValue(P_ICON, brand.getIcon());
        parameterSource.addValue(P_SHORT_DESCRIPTION, brand.getShortDescription());
        parameterSource.addValue(P_FULL_DESCRIPTION, brand.getFullDescription());

        namedParameterJdbcTemplate.update(addSqlQuery, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateBrand(Brand brand) {
        return 0;
    }

    @Override
    public int[] addBrands(Object[] brands) {
        return new int[0];
    }

    @Override
    public int[] updateBrands(Object[] brands) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(brands);
        return namedParameterJdbcTemplate.batchUpdate(updateBrandSqlQuery, batch);
    }

    @Override
    public void deleteAll() {

    }


    private class BrandsResultSetExtractor implements ResultSetExtractor<HashMap<Integer, Brand>> {

        @Override
        public HashMap<Integer, Brand> extractData(ResultSet rs) throws SQLException, DataAccessException {
            HashMap<Integer, Brand> brands = new HashMap<>();
            while(rs.next()) {
                Integer id =                rs.getInt("id_brand");
                Integer papId =             rs.getInt("pap_id");
                String tName =              rs.getString("translit_name");
                String name =               rs.getString("name");
                String icon =               rs.getString("icon");
                // String shortDescription =   rs.getString("short_description");
                // String fullDescription =   rs.getString("full_description");

                Brand brand = new Brand(
                        id,
                        papId,
                        tName,
                        name
                );

                brands.put(papId, brand);
            }
            return brands;
        }
    }
}
