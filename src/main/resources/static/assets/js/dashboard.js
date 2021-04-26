/*
 * Author: Abdullah A Almsaeed
 * Date: 4 Jan 2014
 * Description:
 *      This is a demo file used only for the main dashboard (index.html)
 **/

$(function () {

  'use strict';

  /* Morris.js Charts */
  // Sales chart
  var area = new Morris.Area({
    element   : 'revenue-chart',
    resize    : true,
    data      : [
      { y: '2019 Q1', item1: 60, item2: 62 },
      { y: '2019 Q2', item1: 62, item2: 60 },
      { y: '2019 Q3', item1: 60, item2: 75 },
      { y: '2019 Q4', item1: 62, item2: 80 },
      { y: '2020 Q1', item1: 75, item2: 62 },
      { y: '2020 Q2', item1: 80, item2: 75 },
      { y: '2020 Q3', item1: 77, item2: 85 },
      { y: '2020 Q4', item1: 85, item2: 89 },
      { y: '2021 Q1', item1: 89, item2: 90 },
      { y: '2021 Q2', item1: 90, item2: 96 }
    ],
    xkey      : 'y',
    ykeys     : ['item1', 'item2'],
    labels    : ['Item 1', 'Item 2'],
    lineColors: ['#a0d0e0', '#3c8dbc'],
    hideHover : 'auto'
  });
  // var line = new Morris.Line({
  //   element          : 'line-chart',
  //   resize           : true,
  //   data             : [
  //     { y: '2019 Q1', item1: 2666 },
  //     { y: '2019 Q2', item1: 2778 },
  //     { y: '2019 Q3', item1: 4912 },
  //     { y: '2019 Q4', item1: 3767 },
  //     { y: '2020 Q1', item1: 6810 },
  //     { y: '2020 Q2', item1: 5670 },
  //     { y: '2020 Q3', item1: 4820 },
  //     { y: '2020 Q4', item1: 15073 },
  //     { y: '2021 Q1', item1: 10687 },
  //     { y: '2021 Q2', item1: 8432 }
  //   ],
  //   xkey             : 'y',
  //   ykeys            : ['item1'],
  //   labels           : ['Item 1'],
  //   lineColors       : ['#efefef'],
  //   lineWidth        : 2,
  //   hideHover        : 'auto',
  //   gridTextColor    : '#fff',
  //   gridStrokeWidth  : 0.4,
  //   pointSize        : 4,
  //   pointStrokeColors: ['#efefef'],
  //   gridLineColor    : '#efefef',
  //   gridTextFamily   : 'Open Sans',
  //   gridTextSize     : 10
  // });

  // Donut Chart
  var donut = new Morris.Donut({
    element  : 'sales-chart',
    resize   : true,
    colors   : ['#3c8dbc', '#f56954', '#00a65a'],
    data     : [
      { label: 'Download Sales', value: 12 },
      { label: 'In-Store Sales', value: 30 },
      { label: 'Mail-Order Sales', value: 20 }
    ],
    hideHover: 'auto'
  });


});
