<#include "/templates/header.ftl">
	<form class="form-horizontal" method="post" action="">
		<div class="form-group">
			<label class="control-label col-sm-3">Firstname</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="firstName" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3">lastName</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="lastName" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3">E-mail</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="email" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3">Phone</label>
			<div class="col-sm-9">
				<input type="text" class="form-control" name="phone" />
			</div>
		</div>
		<div class="col-sm-9 col-sm-offset-3">
				<input type="submit" class="btn btn-success" value="Submit" />
			</div>
	</form>
<#include "/templates/footer.ftl"> 