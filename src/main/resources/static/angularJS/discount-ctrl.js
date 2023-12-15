app.controller("discount-ctrl", function($scope, $http, $window, $interval) {
	
	
	 $scope.loadFromLocalStorage = function() {		
		const startTimeString = $window.localStorage.getItem('startTime') || '';
        const endTimeString = $window.localStorage.getItem('endTime') || '';

        $scope.form1 = {
	        startTime: startTimeString ? new Date(startTimeString) : null,
	        endTime: endTimeString ? new Date(endTimeString) : null
    	};
    };

    $scope.loadFromLocalStorage();
	
	
    
   $scope.saveToLocalStorage = function() {
        const storedStartTime = $window.localStorage.getItem('startTime');
        const storedEndTime = $window.localStorage.getItem('endTime');

        if (storedStartTime && storedEndTime) {
            $window.localStorage.setItem('startTime', $scope.form1.startTime.toISOString());
            $window.localStorage.setItem('endTime', $scope.form1.endTime.toISOString());
        } else {
            $window.localStorage.setItem('startTime', $scope.form1.startTime.toISOString());
            $window.localStorage.setItem('endTime', $scope.form1.endTime.toISOString());
        }
    };
    
    
     $scope.onsale = {
        add: function() {
            $scope.saveToLocalStorage();

            Swal.fire({
				icon: 'success',
				title: 'Add to SALE success !',
				showConfirmButton: false,
				timer: 1500
			})

        }
    }


	$scope.onSale = [];
	$scope.noSale = [];


	$scope.initialize = function() {

		$scope.form = {
			image: 'upload0.png',
		}

		$http.get("/rest/products/noSale").then(resp => {
			$scope.noSale = resp.data;
		});

		$http.get("/rest/products/onSale").then(resp => {
			$scope.onSale = resp.data;
		});

		$http.get("/rest/categories").then(resp => {
			$scope.cate = resp.data;
		});

	}

	$scope.initialize();

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
	}

	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/products/${item.id}`, item).then(resp => {
			Swal.fire({
				icon: 'success',
				title: 'SALE Success !',
				showConfirmButton: false,
				timer: 2000
			})
			setTimeout(function() {
				$window.location.reload();
			}, 2000);
		}).catch(error => {
			alert("Lỗi sale !");
			console.log("Error", error);
		});
	}
	
	$scope.remove = function(item) {
		item.sale = 0;
		$http.put(`/rest/products/${item.id}`, item).then(resp => {
			Swal.fire({
				icon: 'error',
				title: 'REMOVE SALE !',
				showConfirmButton: false,
				timer: 1500
			})
			setTimeout(function() {
				$window.location.reload();
			}, 1500);
		}).catch(error => {
			alert("Lỗi sale !");
			console.log("Error", error);
		});
	}

	// PAGE Product No Sale
	$scope.pageNoSale = {
		page: 0,
		size: 10,
		get noSale() {
			var start = this.page * this.size;
			return $scope.noSale.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.noSale.length / this.size)
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	}

});