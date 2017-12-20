function reg(nickname, publicKey, privateKey, publicKeyHash) {
	var url = 'http://localhost:8080/reg';

	var params = new URLSearchParams();
		params.append('Nickname', nickname);
		params.append('PublicKey', publicKey);
		params.append('PrivateKey', privateKey);
		params.append('PublicKeyHash', publicKeyHash);

	axios.post(url, params)
		.then(function (response) {
			console.dir(response);
		})
		.catch(function (error) {
			console.dir(error);
		});	
}