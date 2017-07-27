$(function() {
    var liftoffTime = new Date($(".countdown-timer").attr("date-end")),
        singleProdLiftOff = new Date($(".single-product-action-timer").attr("date-end"));;
    $(".countdown-timer").countdown({until: liftoffTime, format: 'yowdHMS', alwaysExpire: true,
    layout: 
     '<ul>{y<}<li class="countdown-timer__item"><div class="time-numbers"><span class="time-item-big">{y10}</span><span class="time-item-small">{y1}</span></div><div class="time-text">{yl}</div></li>{y>}' + 
     '{o<}<li class="countdown-timer__item"><div class="time-numbers"><span class="time-item-big">{o10}</span><span class="time-item-big">{o1}</span></div><div class="time-text">{ol}</div></li>{o>}' + 
    '{d<}<li class="countdown-timer__item"><div class="time-numbers"><span class="time-item-big">{d10}</span><span class="time-item-small">{d1}</span></div><div class="time-text">{dl}</div></li>{d>}' +
     '{h<}<li class="countdown-timer__item"><div class="time-numbers"><span class="time-item-big">{h10}</span><span class="time-item-small">{h1}</span></div><div class="time-text">{hl}</div></li>{h>}' + 
    '{m<}<li class="countdown-timer__item"><div class="time-numbers"><span class="time-item-big">{m10}</span><span class="time-item-small">{m1}</span></div><div class="time-text">{ml}</div></li>{m>}'+
     '{s<}<li class="countdown-timer__item"><div class="time-numbers"><span class="time-item-big">{s10}</span><span class="time-item-small">{s1}</span></div><div class="time-text">{sl}</div></li>{s>}</ul>' });  
    
    $(".single-product-action-timer").countdown({until: singleProdLiftOff, format: 'yowDHMS', alwaysExpire: true,
    layout: 
     '<ul>{y<}<li class="countdown-timer__item"><div class="time-numbers"><span class="time-item-big">{y10}</span><span class="time-item-small">{y1}</span></div><div class="time-text">{yl}</div></li>{y>}' + 
     '{o<}<li class="countdown-timer__item"><div class="time-numbers"><span class="time-item-big">{o10}</span><span class="time-item-big">{o1}</span></div><div class="time-text">{ol}</div></li>{o>}' + 
    '{d<}<li class="countdown-timer__item"><div class="time-numbers"><span class="time-item-small">{d1}</span></div><div class="time-text">{dl}</div></li>{d>}' +
     '{h<}<li class="countdown-timer__item"><div class="time-numbers"><span class="time-item-big">{h10}</span><span class="time-item-small">{h1}</span></div><div class="time-text">:</div></li>{h>}' + 
    '{m<}<li class="countdown-timer__item"><div class="time-numbers"><span class="time-item-big">{m10}</span><span class="time-item-small">{m1}</span></div><div class="time-text">:</div></li>{m>}'+
     '{s<}<li class="countdown-timer__item"><div class="time-numbers"><span class="time-item-big">{s10}</span><span class="time-item-small">{s1}</span></div></li>{s>}</ul>' });  
    
});