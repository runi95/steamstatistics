setActiveNav('statisticsnav');

var plot;
var datalist;
var togglelist = {'0': true, '1': true, '2': true, '3': true};

function datatoggle(id) {
    togglelist[id] = !togglelist[id];
    if (!togglelist[id]) {
        document.getElementById("datatoggle" + id).setAttribute("class", "test-disabled");
    } else {
        document.getElementById("datatoggle" + id).setAttribute("class", "");
    }
    var newdata = [];
    for (var id in togglelist) {
        if (togglelist[id]) {
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
        if (jsonData.message.hasOwnProperty('personalgained') && jsonData.message.hasOwnProperty('personallost')) {
            datalist = [{data: jsonData.message.averagegained, color: '#66afaf'}, {
                data: jsonData.message.averagelost,
                color: '#df9066'
            }, {data: jsonData.message.personalgained, color: '#76df76'}, {
                data: jsonData.message.personallost,
                color: '#df5656'
            }];
        } else {
            datalist = [{data: jsonData.message.averagegained, color: '#66afaf'}, {
                data: jsonData.message.averagelost,
                color: '#df9066'
            }];
        }

        document.getElementById("monthlygainvalue").appendChild(document.createTextNode(jsonData.message.monthlygain));
        document.getElementById("monthlygaintext").appendChild(document.createTextNode(" friendships bonded"));
        document.getElementById("monthlylossvalue").appendChild(document.createTextNode(jsonData.message.monthlyloss));
        document.getElementById("monthlylosstext").appendChild(document.createTextNode(" friendships ruined"));
        document.getElementById("joinedusersvalue").appendChild(document.createTextNode(jsonData.message.joinedusers));
        document.getElementById("joineduserstext").appendChild(document.createTextNode(" new accounts"));

        var graphdiv = document.getElementById("graphdiv");
        if (window.innerWidth > 666) {
            graphdiv.setAttribute("class", "graph-wrapper");
            var graphinfo = document.createElement("div");
            graphinfo.setAttribute("class", "graph-info");
            var toggleOne = document.createElement("a");
            toggleOne.setAttribute("id", "datatoggle0");
            toggleOne.setAttribute("href", "javascript:datatoggle('0');");
            var toggleOneDiv = document.createElement("div");
            toggleOneDiv.setAttribute("class", "inner-toggle");
            toggleOneDiv.setAttribute("style", "background: #66afaf;");
            var toggleTwo = document.createElement("a");
            toggleTwo.setAttribute("id", "datatoggle1");
            toggleTwo.setAttribute("href", "javascript:datatoggle('1');");
            var toggleTwoDiv = document.createElement("div");
            toggleTwoDiv.setAttribute("class", "inner-toggle");
            toggleTwoDiv.setAttribute("style", "background: #ffa076;");
            var toggleThree = document.createElement("a");
            toggleThree.setAttribute("id", "datatoggle2");
            toggleThree.setAttribute("href", "javascript:datatoggle('2');");
            var toggleThreeDiv = document.createElement("div");
            toggleThreeDiv.setAttribute("class", "inner-toggle");
            toggleThreeDiv.setAttribute("style", "background: #76df76;");
            var toggleFour = document.createElement("a");
            toggleFour.setAttribute("id", "datatoggle3");
            toggleFour.setAttribute("href", "javascript:datatoggle('3');");
            var toggleFourDiv = document.createElement("div");
            toggleFourDiv.setAttribute("class", "inner-toggle");
            toggleFourDiv.setAttribute("style", "background: #df5656;");
            var graphcontainer = document.createElement("div");
            graphcontainer.setAttribute("class", "graph-container");
            var graphlines = document.createElement("div");
            graphlines.setAttribute("id", "graph-lines");
            var canvasbase = document.createElement("canvas");
            canvasbase.setAttribute("class", "base");
            canvasbase.setAttribute("width", "550");
            canvasbase.setAttribute("height", "300");
            var canvasoverlay = document.createElement("canvas");
            canvasoverlay.setAttribute("class", "overlay");
            canvasoverlay.setAttribute("width", "550");
            canvasoverlay.setAttribute("height", "300");
            canvasoverlay.setAttribute("style", "position: absolute; left: 0px; top: 0px;");

            toggleOne.appendChild(toggleOneDiv);
            toggleOne.appendChild(document.createTextNode("Average Gained"));
            toggleTwo.appendChild(toggleTwoDiv);
            toggleTwo.appendChild(document.createTextNode("Average Lost"));
            toggleThree.appendChild(toggleThreeDiv);
            toggleThree.appendChild(document.createTextNode("Personal Gained"));
            toggleFour.appendChild(toggleFourDiv);
            toggleFour.appendChild(document.createTextNode("Personal Lost"));
            graphinfo.appendChild(toggleOne);
            graphinfo.appendChild(toggleTwo);
            graphinfo.appendChild(toggleThree);
            graphinfo.appendChild(toggleFour);
            graphdiv.appendChild(graphinfo);
            graphlines.appendChild(canvasoverlay);
            graphlines.appendChild(canvasbase);
            graphcontainer.appendChild(graphlines);
            graphdiv.appendChild(graphcontainer);

            plotMyData(datalist);
        } else {
            graphdiv.setAttribute("style", "padding-left: 10px; padding-right: 10px;");
            var table = document.createElement("table");
            var thead = document.createElement("thead");
            var theadTr = document.createElement("tr");
            var theadMonth = document.createElement("th");
            var theadDescription = document.createElement("th");
            var theadCount = document.createElement("th");
            var tbody = document.createElement("tbody");

            table.setAttribute("class", "table table-condensed");
            theadMonth.innerText = "Month";
            theadDescription.innerText = "Description";
            theadCount.innerText = "Friends";

            for (var j = 0; j < 12; j++) {
                for (var i = 0; i < datalist.length; i++) {
                    var desc;
                    var clr = datalist[i].color;
                    switch (i) {
                        case 0:
                            desc = "Average Gained";
                            break;
                        case 1:
                            desc = "Average Lost";
                            break;
                        case 2:
                            desc = "Personal Gained";
                            break;
                        case 3:
                            desc = "Personal Lost";
                            break;
                    }
                    //console.log("j(" + j + ")");
                    var tr = document.createElement("tr");
                    tr.setAttribute("style", "background-color: " + clr + ";");
                    var tdMonth = document.createElement("td");
                    var tdDesc = document.createElement("td");
                    var tdCount = document.createElement("td");
                    tdMonth.innerText = datalist[i].data[j][0];
                    tdDesc.innerText = desc;
                    tdCount.innerText = datalist[i].data[j][1];
                    tr.appendChild(tdMonth);
                    tr.appendChild(tdDesc);
                    tr.appendChild(tdCount);
                    tbody.appendChild(tr);
                }
            }

            theadTr.appendChild(theadMonth);
            theadTr.appendChild(theadDescription);
            theadTr.appendChild(theadCount);
            thead.appendChild(theadTr);
            table.appendChild(thead);
            table.appendChild(tbody);
            graphdiv.appendChild(table);
        }
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