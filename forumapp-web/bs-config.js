/**
 * Proxy configuration file for angular lite-server
 * Server searches for this file during lite-server initialization
 *
 * @type {*|(function(): HPM)}
 */

var proxyMiddleware = require('http-proxy-middleware');
var fallbackMiddleware = require('connect-history-api-fallback');

module.exports = {
  server: {
    middleware: {
      1: proxyMiddleware('/api', {
        target: 'http://localhost:8081/forum',
        changeOrigin: true   // for vhosted sites, changes host header to match to target's host
      }),

      2: fallbackMiddleware({
        index: '/index.html', verbose: true
      })
    }
  }
};
