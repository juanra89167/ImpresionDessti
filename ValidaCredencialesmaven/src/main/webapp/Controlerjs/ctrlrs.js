var tabla = angular.module('app1', ['ngAnimate', 'ngTouch', 'ui.grid', 'ui.grid.selection' ]);

tabla.controller('MainCtrl', ['$scope', '$http', '$timeout', '$interval', 'uiGridConstants', function ($scope, $http, $timeout, $interval, uiGridConstants) {
	 console.log("Controller is up.");
  $scope.gridOptions1 = {
		enableRowSelection: true,
        enableSelectAll: true,
        enableFullRowSelection: true,
        enableFiltering: true,
      //  paginationPageSizes: [25, 50, 75],
        //paginationPageSize: 5,
        rowTemplate: '<div ng-click="grid.appScope.doSomething(row)" ng-repeat="(colRenderIndex, col) in colContainer.renderedColumns track by col.uid" class="ui-grid-cell" ng-class="col.colIndex()" ui-grid-cell></div>',
        
  };
$scope.gridOptions1.columnDefs = [
      { name: 'sucursal', width: '15%', enableFiltering: true },
      { name: 'ramo',  width: '15%' ,enableFiltering: true  },//DOES NOT WORK... BUT WHY
      { name: 'poliza', width: '15%', enableFiltering: true },
      { name: 'endoso', width: '10%' , enableFiltering: true },
      { name: 'estatus', width: '10%', enableFiltering: true  },
      { name: 'ACCIONES',  width: '30%' , enableFiltering: true ,
    	  cellTemplate: '<button ng-click="grid.appScope.detener(row.entity.fullName)" ><img src="img/pausa.png"></button>' +' '+'<button ng-click="grid.appScope.reiniciar(row.entity.fullName)" ><img src="img/undo.png"></button>'+'<button ng-click="grid.appScope.cancelar(row.entity.fullName)"> <img src="img/cancel.png"></button>'+'<button ng-click="grid.appScope.imprimir(row.entity.fullName)"> <img src="img/print.png"></button>'+'<button ng-click="grid.appScope.xml(row.entity.fullName)"> <img src="img/xml.png"></button>'}
    
    ];

$scope.filterGender = function(){
    $scope.state = $scope.gridApi.saveState.save();
    $scope.state.columns[1].filters[1]({term: $scope.genderFilter || '*'});
    $scope.gridApi.saveState.restore( $scope, $scope.state );
    alert('Name: '+ $scope.state );
    console.log($scope.state);//for you to understand what are in state
  };

$scope.gridOptions1.multiSelect = true;
    $scope.getSelectedRows = function() {
	    $scope.mySelectedRows = $scope.gridApi.selection.getSelectedRows();
	  };
	 
 $scope.gridOptions1.onRegisterApi = function(gridApi){
	      //set gridApi on scope
 $scope.gridApi = gridApi;
 
 gridApi.selection.on.rowSelectionChanged($scope,function(row){
	 angular.forEach($scope.gridOptions1.data, function(value, key){
	      if(value.sucursal == row.entity.sucursal){
	    	  //alert('Name: '+ value.sucursal);
	      $scope.searchDetalle(value.sucursal);}
	   });
	 
  //   var msg = 'row selected ' + row.isSelected;
   //  var msg1 = row.entity.sucursal;
     //alert('Name: '+ msg1);
   });

 
 };
  


	  
   $scope.doSomething = function(row){   }
 
   
   
   
   
  
    $scope.buttonClick = function() {
    	 gridApi.selection.on.rowSelectionChanged($scope,function(row){
    	  
    	     var msg1 = row.entity.name;
    	     alert('Name000: '+ msg1 );
    	   });
    };
    $scope.detener= function() {
  
   	 
   	 
   };
  $scope.reiniciar = function() {
  gridApi.selection.on.rowSelectionChanged($scope,function(row){
  	  
  	     var msg1 = row.entity.name;
  	     alert('Name000: '+ msg1 );
  	   });
  };
  $scope.cancelar = function() {
 	 gridApi.selection.on.rowSelectionChanged($scope,function(row){
 	  
 	     var msg1 = row.entity.name;
 	     alert('Name000: '+ msg1 );
 	   });
 };
 $scope.imprimir = function() {
	 gridApi.selection.on.rowSelectionChanged($scope,function(row){
	  
	     var msg1 = row.entity.name;
	     alert('Name000: '+ msg1 );
	   });
};
$scope.xml = function() {
	 gridApi.selection.on.rowSelectionChanged($scope,function(row){
	  
	     var msg1 = row.entity.name;
	     alert('Name000: '+ msg1 );
	   });
};

$scope.gridOptions = angular.copy($scope.gridOptions1);
  
$scope.gridOptions2 = {
    enableRowSelection: false,
        enableSelectAll: true,
    //enableFiltering: false,
}
        $scope.gridOptions2.columnDefs= [
      { name: 'sucursal', width: '15%' },
      { name: 'INCISO', width: '15%' },
      { name: 'name', width: '15%' },
      { name: 'DESCRIPCION',  width: '15%' },
      { name: 'estaus', width: '10%' },
      { name: 'ACCIONES', width: '30%',
    	  cellTemplate: '<button ng-click="grid.appScope.buttonClick(row.entity.fullName)"> <img src="img/pausa.png" > </button>' +' '+'<button ng-click="grid.appScope.buttonClick(row.entity.fullName)"><img src="img/undo.png" ></button>'+'<button ng-click="grid.appScope.buttonClick(row.entity.fullName)"><img src="img/cancel.png" ></button>'+'<button ng-click="grid.appScope.buttonClick(row.entity.fullName)"><img src="img/print.png" ></button>'+'<button ng-click="grid.appScope.buttonClick(row.entity.fullName)"> <img src="img/xml.png"></button>'
    	  }
      
    ];
 
 $http.get('/ValidaCredencialesmaven/prueba/actor')
 .success(function(data) {
   $scope.gridOptions1.data = data;
  
   $scope.mySelectedRows = $scope.gridApi.selection.getSelectedRows();
   $timeout(function() {
     if($scope.gridApi.selection.selectRow){
        $scope.gridApi.selection.selectRow($scope.gridOptions1.data[0]);
     }});
   $scope.grid1();
 });
 
 $scope.searchDetalle = function(name) {
	  alert('Name: '+ name);
	 $http.get('/ValidaCredencialesmaven/prueba/actor/search/' + name)
	 .success(function(data) { 
	   $scope.gridOption.data = data;
	  /* angular.forEach(data, function(element) {
           alert('[main] actor: ' + element.poliza);
	        });*/

	 });
	     };

 
 $scope.gridOption = angular.copy($scope.gridOptions2);
// $scope.gridApi.core.notifyDataChange(uiGridConstants.dataChange.ALL);
 $scope.grid1=function(){
   $scope.gridOptions = angular.copy($scope.gridOptions1);
   $scope.gridApi.core.notifyDataChange(uiGridConstants.dataChange.ALL);

 }
 $scope.grid2=function(){
   $scope.gridOptions = angular.copy($scope.gridOptions2);
   $scope.gridApi.core.notifyDataChange(uiGridConstants.dataChange.ALL);
 }
}]);

