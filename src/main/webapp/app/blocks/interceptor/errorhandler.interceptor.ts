ErrorHandlerInterceptor.$inject = ['$q', '$rootScope'];

export function ErrorHandlerInterceptor ($q, $rootScope) {
    var service = {
        responseError: responseError
    };

    return service;

    function responseError (response) {
        if (!(response.status === 401 && (response.data === '' || (response.data.path && response.data.path.indexOf('/api/account') === 0 )))) {
            $rootScope.$emit('myappApp.httpError', response);
        }
        return $q.reject(response);
    }
}
