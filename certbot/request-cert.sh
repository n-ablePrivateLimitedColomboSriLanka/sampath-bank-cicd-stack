docker run --rm -it --name certbot -v certbot-certs:/etc/letsencrypt certbot/certbot \
	--eff-email \
	-m socialexpz1@gmail.com \
	--agree-tos certonly \
	--manual \
	-d '*.nableintegration.ml'
