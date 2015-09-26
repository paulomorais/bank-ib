function handleLoginRequest(xhr, status, args) {
	if (!args.validationFailed) {
		 window.location = "./index.xhtml";
	}
}