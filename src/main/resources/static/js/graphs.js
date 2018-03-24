setActiveNav('statisticsnav');

var plot;
var datalist;
var togglelist = {'0' : true, '1' : true, '2' : true, '3' : true};

function datatoggle(id) {
    togglelist[id] = !togglelist[id];
    if(!togglelist[id]) {
        document.getElementById("datatoggle" + id).setAttribute("class", "test-disabled");
    } else {
        document.getElementById("datatoggle" + id).setAttribute("class", "");
    }
    var newdata = [];
    for(var id in togglelist) {
        if(togglelist[id]) {
            newdata.push(datalist[id]);
        }
    }
    redrawData(newdata);
}

$.ajax({
    type: "GET",
    dataType: "json",
    url: "/monthlygraph",
    success: function (jsonData) {
        if(jsonData.message.hasOwnProperty('personalgained') && jsonData.message.hasOwnProperty('personallost')) {
            datalist = [{data : jsonData.message.averagegained, color : '#66afaf'}, {data : jsonData.message.averagelost, color : '#df9066'}, {data : jsonData.message.personalgained, color : '#76df76'}, {data : jsonData.message.personallost, color : '#df5656'}];
        } else {
            datalist = [{data : jsonData.message.averagegained, color : '#66afaf'}, {data : jsonData.message.averagelost, color : '#df9066'}];
        }

        document.getElementById("monthlygainvalue").appendChild(document.createTextNode(jsonData.message.monthlygain));
        document.getElementById("monthlygaintext").appendChild(document.createTextNode(" friendships bonded"));
        document.getElementById("monthlylossvalue").appendChild(document.createTextNode(jsonData.message.monthlyloss));
        document.getElementById("monthlylosstext").appendChild(document.createTextNode(" friendships ruined"));
        document.getElementById("joinedusersvalue").appendChild(document.createTextNode(jsonData.message.joinedusers));
        document.getElementById("joineduserstext").appendChild(document.createTextNode(" new accounts"));

        plotMyData(datalist);
    }
});

function redrawData(myData) {
    plot.setData(myData);
    plot.draw();
}

function plotMyData(myData) {
    plot = $.plot($('#graph-lines'), myData, {
        series: {
            points: {
                show: true,
                radius: 5
            },
            lines: {
                show: true
            },
            shadowSize: 0
        },
        grid: {
            color: '#646464',
            borderColor: 'transparent',
            borderWidth: 20,
            hoverable: true
        },
        xaxis: {
            mode: "categories",
            tickLength: 0
        },
        yaxis: {
            tickDecimals: 0,
            position: "left",
        }
    });

    $('#lines').on('click', function (e) {
        $('#bars').removeClass('active');
        $('#graph-bars').fadeOut();
        $(this).addClass('active');
        $('#graph-lines').fadeIn();
        e.preventDefault();
    });

    function showTooltip(x, y, contents) {
        $('<div id="tooltip">' + contents + '</div>').css({
            top: y - 16,
            left: x + 20,
        }).appendTo('body').fadeIn();
    }

    var previousPoint = null;

    $('#graph-lines').bind('plothover', function (event, pos, item) {
        if (item) {
            if (previousPoint != item.dataIndex) {
                previousPoint = item.dataIndex;
                $('#tooltip').remove();
                var x = item.datapoint[0],
                    y = item.datapoint[1];
                showTooltip(item.pageX, item.pageY, y + ' friends in ' + item.series.data[item.dataIndex][0]);
            }
        } else {
            $('#tooltip').remove();
            previousPoint = null;
        }
    });
}