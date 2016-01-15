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