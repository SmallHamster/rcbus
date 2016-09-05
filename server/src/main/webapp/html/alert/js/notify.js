var $common = {
	fn : {
		notify : function(message) {
			var notification = new NotificationFx({
				message : message,
				layout : 'growl',
				effect : 'scale',
				type : 'success', // notice, warning, error or success
			});
			notification.show();
		}
	}
}