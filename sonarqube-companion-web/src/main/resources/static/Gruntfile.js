module.exports = function(grunt) {

    var globalConfig = {
    };

    grunt
        .initConfig({
            globalConfig: globalConfig,
            pkg : grunt.file.readJSON('package.json'),
            connect : {
                proxies : [ {
                    context : '/api',
                    host : '127.0.0.1',
                    port : 8080,
                    rewrite : {
                        '^/api' : '/api'
                    }
                } ],
                server : {
                    options : {
                        port : 9000,
                        base : '',
                        middleware: function (connect, options, defaultMiddleware) {
                            if (!Array.isArray(options.base)) {
                                options.base = [options.base];
                            }

                            // Setup the proxy
                            var middlewares = [require('grunt-connect-proxy/lib/utils').proxyRequest];

                            // Serve static files.
                            options.base.forEach(function(base) {
                                middlewares.push(connect.static(base));
                            });

                            // Make directory browse-able.
                            //var directory = options.directory || options.base[options.base.length - 1];
                            //middlewares.push(connect.directory(directory));

                            return middlewares;
                        }
                    }
                }
            },
            watch : {
            }
        });

    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-connect');
    grunt.loadNpmTasks('grunt-connect-proxy');

    grunt.registerTask('default', [ 'serve' ]);
    grunt.registerTask('serve', [ 'configureProxies:server', 'connect:server', 'watch' ]);

};