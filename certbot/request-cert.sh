docker run --rm -it --name certbot -v certbot-certs:/etc/letsencrypt certbot/certbot \
	--eff-email \
	-m iresh@n-able.biz \
	--agree-tos certonly \
	--manual \
	-d '*.test.ci.appinnov.nblrnd.biz'