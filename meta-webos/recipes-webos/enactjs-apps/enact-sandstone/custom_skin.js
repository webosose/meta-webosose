if (typeof WebOSServiceBridge === 'function') {
	const bridge = new WebOSServiceBridge();
	bridge.onservicecallback = (msg) => {
			try {
					const parsedMsg = JSON.parse(msg);
					if (parsedMsg.returnValue) {
							const sheet = document.createElement('style');
							sheet.id = 'custom-skin';
							sheet.innerHTML = JSON.parse(parsedMsg.settings.theme).colors;
							document.getElementById('custom-skin')?.remove();
							document.body?.appendChild(sheet);
					}
			} catch {
			}
	};
	bridge.call('luna://com.webos.service.settings/getSystemSettings', '{"category":"customUi","keys":["theme"],"subscribe":true}');
}
