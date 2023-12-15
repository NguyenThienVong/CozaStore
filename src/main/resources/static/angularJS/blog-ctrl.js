app.controller("blog-ctrl", function($scope, $http) {

	$scope.items = [];
	$scope.form = {};

	$scope.initialize = function() {
		// Load product
		$http.get("/rest/blogs").then(resp => {
			$scope.items = resp.data;
			$scope.form = {
				createDate: new Date(),
				image: 'upload.png',
			}
			$scope.items.forEach(item => {
				item.create_date = new Date(item.create_date)
			})
		});

		// Load categories
		$http.get("/rest/accounts").then(resp => {
			$scope.accounts = resp.data;
		});

	}

	// Khởi Đầu
	$scope.initialize();

	// Xóa Form 
	$scope.reset = function() {
		$scope.form = {
			create_date: new Date(),
			image: 'upload.png',
		};
	}
	//Hien thi len form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
	}

	//them sp moi
	$scope.create = function() {
		if (!$scope.form.title || $scope.form.image === 'upload.png' || !$scope.form.short_describe || !$scope.form.describe || !$scope.form.accounts.username) {
			Swal.fire({
				icon: 'warning',
				title: 'Lỗi !',
				text: "Vui lòng nhập đầy đủ thông tin sản phẩm !",
				showConfirmButton: false,
				timer: 1500
			})
			return;
		} else {
			var item = angular.copy($scope.form);
			$http.post(`/rest/blogs`, item).then(resp => {
				resp.data.createDate = new Date(resp.data.createDate)
				$scope.items.push(resp.data);
				$scope.reset();
				Swal.fire({
					icon: 'success',
					title: 'Add Successfully !',
					showConfirmButton: false,
					timer: 1500
				})
			}).catch(error => {
				alert("Lỗi thêm mới sản phẩm !");
				console.log("Error", error);
			});
		}
	}
	//cap nhat sp
	$scope.update = function() {
		if (!$scope.form.title || $scope.form.image === 'upload.png' || !$scope.form.short_describe || !$scope.form.describe || !$scope.form.accounts.username) {
			Swal.fire({
				icon: 'warning',
				title: 'Lỗi !',
				text: "Vui lòng nhập đầy đủ thông tin sản phẩm !",
				showConfirmButton: false,
				timer: 1500
			})
			return;
		} else {
			var item = angular.copy($scope.form);
			$http.put(`/rest/blogs/${item.id}`, item).then(resp => {
				var index = $scope.items.findIndex(p => p.id == item.id);
				$scope.items[index] = item;
				$scope.reset();
				Swal.fire({
					icon: 'success',
					title: 'Update Successfully !',
					showConfirmButton: false,
					timer: 1500
				})
			}).catch(error => {
				alert("Lỗi cập nhật sản phẩm !");
				console.log("Error", error);
			});
		}
	}
	//xoa sp
	$scope.delete = function(item) {
		$http.delete(`/rest/blogs/${item.id}`).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items.splice(index, 1);
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
			alert("Lỗi xóa sản phẩm !");
			console.log("Error", error);
		});
	}
	//upload hinh
	$scope.imageChanged = function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image = resp.data.name;
		}).catch(error => {
			alert("Upload Image False !");
			console.log("Error", error);
		})
	}

});