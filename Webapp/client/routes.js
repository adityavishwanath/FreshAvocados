Router.route('/', function () {
  this.render('home');
});

Router.route('/login', function () {
  this.render('login');
});

Router.route('/register', function () {
  //var item = Items.findOne({_id: this.params._id});
  this.render('register');
});

Router.route('/dashboard', function(){
  this.render('dashboard');
});