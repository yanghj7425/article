var app = angular.module('myApp', []);

app.controller('transFileController', function ($scope, $http) {


    $scope.fnclick = function () {
        alert('1');
    }


    /**
    * @desc 换届凭证上传
    */
    $scope.uploadFile = function () {
        var formData = new FormData();
        var file = document.getElementById("fileUpload").files[0];

        formData.append('file', file);

        if (!file) {
            return;
        }
        $http({
            method: 'POST',
            url: 'http://localhost:10010/a/orgExchangeFileServlet',
            data: formData,
            headers: {
                'Content-Type': undefined
            },
            transformRequest: angular.identity
        }).success(function (data) {
            var importErrorDataSize = data.length;

            $scope.importErrorData = data;

            if (importErrorDataSize < 1) {

            } else {

            }

        }).error(function (data) {
            console.log('upload fail');
        })
    }

    $scope.fileDownload = function () {
        var param = {};
        param['fileName'] = 'invokeWithinTransaction.JPG';
        $http({
            method: 'GET',
            url: 'orgExchangeFileServlet',
            params: param
        }).then(function (data) {
            console.log(data);
        }).catch(function (data) {
            console.log(data);
        })
    }
})