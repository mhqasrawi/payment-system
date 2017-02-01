<div class="container" style="margin-top: 40px">
		<h2>Create New Payment Purpose</h2>
		<form action="${request.getAttribute("
			javax.servlet.forward.request_uri")}" method="post">
			<div class="form-group">
				<label class="padding2">Short Code:</label> <input type="text"
					class="form-control" name="shortCode">
			</div>
			<div class="form-group">
				<label class="padding2">Description:</label> <input type="text"
					class="form-control" name="description">
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
		</div>