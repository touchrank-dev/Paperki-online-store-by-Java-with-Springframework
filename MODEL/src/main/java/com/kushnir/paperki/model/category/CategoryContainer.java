package com.kushnir.paperki.model.category;

import com.kushnir.paperki.model.Category;

import java.util.HashMap;

public class CategoryContainer {
    HashMap<Integer, Category> parents = new HashMap<>();
    HashMap<Integer, Category> children = new HashMap<>();

    public CategoryContainer() {
    }

    public CategoryContainer(HashMap<Integer, Category> parents,
                             HashMap<Integer, Category> children) {
        this.parents = parents;
        this.children = children;
    }

    public HashMap<Integer, Category> getParents() {
        return parents;
    }

    public void setParents(HashMap<Integer, Category> parents) {
        this.parents = parents;
    }

    public HashMap<Integer, Category> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Integer, Category> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryContainer that = (CategoryContainer) o;

        if (parents != null ? !parents.equals(that.parents) : that.parents != null) return false;
        return children != null ? children.equals(that.children) : that.children == null;
    }

    @Override
    public int hashCode() {
        int result = parents != null ? parents.hashCode() : 0;
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CategoryContainer{" +
                "parents=" + parents +
                ", children=" + children +
                '}';
    }
}
