app.controller("voucher-ctrl", function($scope, $http) {

	$scope.vous = [];
	$scope.form = {};

	$scope.initialize = function() {

		// Load vouchers
		$http.get("/rest/vouchers").then(resp => {
			$scope.vous = resp.data;
			$scope.vous.forEach(item => {
				item.start_date = new Date(item.start_date),
				item.end_date = new Date(item.end_date)
			})
		});


	}
	//Khởi Đầu
	$scope.initialize();

	// Xóa Form
	$scope.reset = function() {
		$scope.form = {};
	}
	//hien thi len form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
	}
	//them sp moi
	$scope.create = function() {
			if (!$scope.form.id || !$scope.form.price || !$scope.form.decribe || !$scope.form.quantity || !$scope.form.start_date || !$scope.form.end_date) {
        Swal.fire({
            icon: 'warning',
            title: 'Lỗi !',
            text: "Vui lòng nhập đầy đủ thông tin voucher !",
            showConfirmButton: false,
            timer: 1500
        })
        return;
    } else {
		var item = angular.copy($scope.form);
		$http.post(`/rest/vouchers`, item).then(resp => {
			$scope.vous.push(resp.data);
			$scope.reset();
			Swal.fire({
				  icon: 'success',
				  title: 'Add Successfully !',
				  showConfirmButton: false,
				  timer: 1500
				})
		}).catch(error => {
			alert("Có Lỗi !");
			console.log("Error", error);
		});
		}
	}
	//cap nhat sp
	$scope.update = function() {
			if (!$scope.form.id || !$scope.form.price || !$scope.form.decribe || !$scope.form.quantity || !$scope.form.start_date || !$scope.form.end_date) {
        Swal.fire({
            icon: 'warning',
            title: 'Lỗi !',
            text: "Vui lòng nhập đầy đủ thông tin voucher !",
            showConfirmButton: false,
            timer: 1500
        })
        return;
    } else {
		var item = angular.copy($scope.form);
		$http.put(`/rest/vouchers/${item.id}`, item).then(resp => {
			var index = $scope.vous.findIndex(p => p.id == item.id);
			$scope.vous[index] = item;
			$scope.reset();
			Swal.fire({
				  icon: 'success',
				  title: 'Update Successfully !',
				  showConfirmButton: false,
				  timer: 1500
				})
		}).catch(error => {
			alert("Có Lỗi !");
			console.log("Error", error);
		});
		}
	}
	//xoa sp
	$scope.delete = function(item) {
		$http.delete(`/rest/vouchers/${item.id}`).then(resp => {
			var index = $scope.vous.findIndex(p => p.id == item.id);
			$scope.vous.splice(index, 1);
			$scope.reset();
			//Khởi Đầu
			$scope.initialize();
			Swal.fire({
				  icon: 'success',
				  title: 'Delete Successfully !',
				  showConfirmButton: false,
				  timer: 1500
				})
		}).catch(error => {
			alert("Có Lỗi !");
			console.log("Error", error);
		});
	}


});