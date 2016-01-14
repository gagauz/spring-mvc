<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" href="/static/bootstrap-3.3.6-dist/css/bootstrap.min.css" />
		<link rel="stylesheet" href="/static/bootstrap-3.3.6-dist/css/bootstrap-theme.min.css" />
		<style>
			.mainmenu{clear:both;height:30px;}
			.template{display:none}
		</style>
	</head>
	<body>
		<div class="mainmenu" data-get="mainMenu" data-get-template="#mainMenu-template">
			<div class="template" id="mainMenu-template">
				{{#list}}
				<a class="" href="{{url}}">{{name}}</a>
				{{/list}}
			</div>
		</div>
		<div class="">
			<div class="leftmenu" data-get="leftMenu" data-get-list="list" data-get-item="item">
			</div>
			<div class="content" data-get="content" data-get-param="">
			</div>
		</div>
		<script type="text/javascript" src="/static/jquery/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="/static/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="/static/handlebars/handlebars-v4.0.5.js"></script>
		<script type="text/javascript">
			var handlers={}, params={};
			$('[data-get]').each(function(i, e){
				console.log(e);
				e = $(e);
				params[e.data('get')]=e.data('get-param');
				handlers[e.data('get')] = resolveHandler(e);
			});
			$.post('/data-get.json', params, function(data) {
				for (var k in data) {
					handlers[k](data[k]);
				}
			}, 'json');
			
			function resolveHandler(e) {
				var tpl_id = e.data('get-template')
				if (tpl_id) {
					return function(d) {
						console.log('handle', d, ' on ', e);
						if (d) {
							var tpl = Handlebars.compile(e.find(tpl_id).detach().html());
							e.html(tpl(d));
						} 
					};
				} else {
					return function(d) {
						console.log('handle', d, ' on ', e);
						e.html(d);
					};
				}
			} 
			
			Handlebars.registerHelper('list', function(options) {
			  var out="";
			  for(var i=0, l=options.data.root.length; i<l; i++) {
			    out += options.fn(options.data.root[i]);
			  }
			  return out;
			});
		</script>
	</body>
</html>