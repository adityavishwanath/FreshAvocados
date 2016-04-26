import { Meteor } from 'meteor/meteor';

Meteor.methods({
	getData: function() {
		HTTP.call("GET", 'http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=yedukp76ffytfuy24zsqk7f5&q=Jack&page_limit=1', function(err, res){
				if (err) {
					console.log(err);
				} else {
					console.log(res);
					return res;
				}
		});
	},
});