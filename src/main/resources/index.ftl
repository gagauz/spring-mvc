<#include "header.ftl">
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
<#include "footer.ftl">
