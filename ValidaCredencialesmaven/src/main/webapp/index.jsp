<!doctype html>
<html ng-app="app1">
  <head>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.js"></script>
      <link rel="stylesheet" href="http://ui-grid.info/release/ui-grid-unstable.min.css">
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular-touch.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular-animate.js"></script>
    <script src="http://ui-grid.info/docs/grunt-scripts/csv.js"></script>
    <script src="http://ui-grid.info/docs/grunt-scripts/pdfmake.js"></script>
    <script src="http://ui-grid.info/docs/grunt-scripts/vfs_fonts.js"></script>
    <script src="http://ui-grid.info/release/ui-grid-unstable.js"></script>
    <link rel="stylesheet" href="http://ui-grid.info/release/ui-grid-unstable.css" type="text/css">
    <link rel="stylesheet" href="css/main.css" type="text/css">
  </head>
  <body>

<div ng-controller="MainCtrl">
 <h4>PÓLIZAS</h4>
  <hr>
   
 
 
 
  <div id="grid1" ui-grid="gridOptions" ui-grid-selection class="grid" ui-grid-paginationclass="grid"></div>
  
  <br>
  <h4>DETALLE</h4>
  	<hr>
  	
  	<br>
    	<div id="grid2" ui-grid="gridOption" ui-grid-selection class="grid"></div>
 	
</div>


    <script src="Controlerjs/ctrlrs.js"></script>
  </body>
</html>
