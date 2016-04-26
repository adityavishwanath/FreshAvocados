import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';
import { Meteor } from 'meteor/meteor';
import { Results } from '../imports/api/results.js';
import './main.html';
import '../imports/ui/register.html';
import '../imports/ui/login.html';
import '../imports/ui/home.html';
import '../imports/ui/dashboard.html';
import '../imports/ui/dashboard2.html';
import './routes.js';

if (Meteor.isClient) {
	Template.register.events ({
		'submit form': function(event) {
			event.preventDefault();
			console.log("form submitted");
			var email = event.target.email.value;
			
			var	username =  event.target.username.value;
			var	password =  event.target.password.value;
			var	cPassword =  event.target.confirmPassword.value;
			
			var profile = {
				username: event.target.username.value,
				password: event.target.password.value,
				mail: event.target.email.value,
				firstName:  event.target.firstName.value,
				lastName:  event.target.lastName.value,
				major: event.target.major.value,
				bio:  event.target.Bio.value
			}

			Accounts.createUser({username: username, password: password, email: email, profile: profile}, function(err) {
				if (err) {
					console.log("I AM JAKE FROM STATE FARM");
				} else {
					Router.go("/");
				}
			});
			
		}

	});

	Template.dashboard2.helpers({
	  firstName: function() {
	    return Meteor.user().profile.firstName;
	  },

	  lastName: function() {
	    return Meteor.user().profile.lastName;
	  },

	  email: function() {
	    return Meteor.user().profile.mail;
	  },

      results: function() {
        return Results.find({});
      },
	});

	Template.dashboard.helpers({
	  firstName: function() {
	    return Meteor.user().profile.firstName;
	  },

	  lastName: function() {
	    return Meteor.user().profile.lastName;
	  },

	  email: function() {
	    return Meteor.user().profile.mail;
	  },
	});

	Template.dashboard.events ({
		'click #logout': function(event) {
			event.preventDefault();
			console.log("Logout");
			Meteor.logout(function(err){
				Router.go('/');
			});
		},

		'click #searchMovies': function(event) {
			
			Meteor.http.call("GET", 'http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=yedukp76ffytfuy24zsqk7f5&q=jack',function(error,result){
     			var listMovies = JSON.parse(result.content).movies;
     			
     			// console.log(listMovies[0]);
     			// console.log(listMovies[0].title);
     			for (var i = 0; i < 10; i++) {
     				Results.insert({
                  		title: listMovies[i].title,
                  		year: listMovies[i].year,
                  		runtime: listMovies[i].runtime,
                  		synopsis: listMovies[i].synopsis,
                  		thumb: listMovies[i].posters.thumbnail,
                	});
     			}
     			
     			
			});
			event.preventDefault();
			Router.go('/movieList');
		}

	});

	Template.login.events ({
		'submit form': function(event) {
			event.preventDefault();
			console.log("form submitted");
			var username = event.target.username.value;
			var password = event.target.password.value;
			Meteor.loginWithPassword(username, password, function(err){
				if (err) {
					console.log(err);
				} else {
					Router.go('/dashboard');
				}
			});
  		}	

	});

	Template.home.events({
		'click #goLogin': function(event) {
			console.log("HI");
			Router.go('/login');
		},

		'click #goRegister': function(event) {
			Router.go('/register');
		},
	});
}
