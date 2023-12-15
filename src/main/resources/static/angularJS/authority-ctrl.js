app.controller("authority-ctrl",function($scope,$http,$location){
	
	$scope.admins = [];
	$scope.customers = [];
    $scope.roles = [];
    $scope.authorities = [];

    $scope.initialize = function(){
		
        // Load All Roles
        $http.get("/rest/roles").then(resp => {
            $scope.roles = resp.data;
        })

        //load staffs and directors(administrators)
        $http.get("/rest/accounts").then(resp => {
            $scope.admins = resp.data;
        })

        //load authorities of staffs and directors
        $http.get("/rest/authorities").then(resp => {
            $scope.authorities = resp.data;
         }).catch(error => {
            Swal.fire('Fail', '', 'info')
            console.log("Error", error);
        })
            
    }

    $scope.authority_of = function(acc,roles){
        if($scope.authorities){
            return $scope.authorities.find(ur => ur.accounts.username == acc.username && ur.roles.id==roles.id);
        }
    }

    $scope.authority_changed = function(acc,roles){
        var authority = $scope.authority_of(acc,roles);
        if(authority){//da cap quyen => thu hoi quyen(xoa)
            $scope.revoke_authority(authority);
        }
        else{//chua duoc cap quyen => cap quyen(them moi)
            authority = {accounts:acc,roles:roles};
            $scope.grant_authority(authority);
        }
    }

    //Them moi authority
    $scope.grant_authority = function(authorities){
        $http.post(`/rest/authorities`,authorities).then(resp => {
            $scope.authorities.push(resp.data)
	            Swal.fire({
					  icon: 'success',
					  title: 'Add Successfully !',
					  showConfirmButton: false,
					  timer: 1500
					})
        }).catch(error => {
	            Swal.fire({
					  icon: 'error',
					  title: 'Add fail !',
					  showConfirmButton: false,
					  timer: 1500
					})
            console.log("Error", error);
        })
    }

    // Xóa Auth
    $scope.revoke_authority = function(authorities){
        $http.delete(`/rest/authorities/${authorities.id}`).then(resp => {
            var index = $scope.authorities.findIndex(a => a.id == authorities.id);
            $scope.authorities.splice(index,1);
             Swal.fire({
					  icon: 'success',
					  title: 'Remove Successfully !',
					  showConfirmButton: false,
					  timer: 1500
					})
        }).catch(error => {
            Swal.fire({
					  icon: 'error',
					  title: 'Remove fail !',
					  showConfirmButton: false,
					  timer: 1500
					})
            console.log("Error",error);
        })
    }
    
    
    
    $scope.initialize();
    

	
});