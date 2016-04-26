import { Meteor } from 'meteor/meteor';
import '../imports/api/results.js';
var connectHandler = WebApp.rawConnectHandlers;

Meteor.startup(() => {
  // code to run on server at startup
  connectHandler.use(function (req, res, next) {
    res.setHeader('access-control-allow-origin', '*'); // 2592000s / 30 days
    	return next();
  	})
});
