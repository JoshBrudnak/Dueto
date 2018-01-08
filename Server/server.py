
import os, sys, json, logging, requests, uuid

import cyclone.web
from cyclone.bottle import run

from twisted.internet import reactor, ssl
from twisted.python import log

from OpenSSL import crypto

from secrets import *

version = "0.0.1"

# ssl_certificate_path = "/etc/letsencrypt/live/<<domain-name>>/cert.pem"
# ssl_certificate_key_path = "/etc/letsencrypt/live/<<domain-name>>/privkey.pem"
# ssl_certificate_chain_path = "/etc/letsencrypt/live/<<domain-name>>/chain.pem"

# ssl_certificate_key = crypto.load_privatekey(crypto.FILETYPE_PEM, open(ssl_certificate_key_path, 'rt').read())
# ssl_certificate = crypto.load_certificate(crypto.FILETYPE_PEM, open(ssl_certificate_path, 'rt').read())
# ssl_certificate_chain = [crypto.load_certificate(crypto.FILETYPE_PEM, open(ssl_certificate_chain_path, 'rt').read())]

root = os.path.join(os.path.dirname(__file__), ".")

class BaseHandler(cyclone.web.RequestHandler):
	def get_current_user(self):
		return self.get_secure_cookie("user")

	def prepare(self):
		if not self.current_user:
			loggedInUser = None
		else:
			loggedInUser = self.get_secure_cookie("user")	

class ErrorHandler(BaseHandler):
	def __init__(self, application, request, status_code):
		cyclone.web.RequestHandler.__init__(self, application, request)
		self.set_status(status_code)
		
	def get_error_html(self, status_code, **kwargs):
		if status_code in [404, 500, 503, 403]:
			print "todo"
			# self.render(str(status_code) + ".html")		
	def prepare(self):
		raise cyclone.web.HTTPError(self._status_code)

cyclone.web.ErrorHandler = ErrorHandler

class MyEncoder(json.JSONEncoder):
	def default(self, obj):
		if isinstance(obj, decimal.Decimal):
			return float(obj)
		elif isinstance(obj, datetime.datetime):
			encoded_object = list(obj.timetuple())[0:6]
		else:
			encoded_object =json.JSONEncoder.default(self, obj)
		return encoded_object

class MainHandler(BaseHandler):
	def get(self):
		self.write("Cool")

class AccountHandler(BaseHandler):
	def get(self):
		print "got"

class LoginHandler(BaseHandler):
	def post(self):
		print "post"

class LogoutHandler(BaseHandler):
	def post(self):
		args = convertRequestArgs(self.request.body)
		self.clear_cookie("user")
		self.write(json.dumps({"Result": "Logout successful"}, separators=(', ', ': ')))

class RegistrationHandler(BaseHandler):
	def post(self):
		args = convertRequestArgs(self.request.body)

		captchaSuccess = checkGReCaptchaResponse(args["g-recaptcha-response"], self.request.remote_ip, grecaptchaSecretKey)

		if not captchaSuccess:
			self.write(json.dumps({"Error": "g-recaptcha"}))

		else:
			
			# for each user in users
				# if user.username == args["username"] or user.email == args["email"]
					# self.write(json.dumps({"Result":False}, separators=(', ',': ')))
					# return

			salt, _hash = hasher.getDigest(args["password"])

			result = False

			try:
				# create a new user var
				# set attributes
				# newUser._salt = salt
				# newUser._hash = _hash
				# newUser.verificationKey = uuid.uuid4()
				# (in firebase) (probably)
				result = True

				#generateConfirmationEmail(newUser.email, newUser.verificationKey, newUser.username)
			except Exception, e:
				print e

			self.write(json.dumps({"Result":result}, separators=(', ',': ')))

class VerifyAccount(BaseHandler):
	def get(self, verificationKey):
		
		success = False

		# for user in users
			# if user.isVerified == False and user.verificationKey == verificationKey:
				# user.isVerified = True
				# user.verificationKey = None
				# update
				#success = True
		# set cookie if success == True
		self.render("landing.html")

def generateConfirmationEmail(recipient, registrationKey, username):
    gmail_user = getGmailUsername()
    gmail_pwd = getGmailPassword()
    FROM = getGmailUsername()
    TO = recipient if type(recipient) is list else [recipient]
    SUBJECT = "Account Verification"
    TEXT = "Hey there " + username + ", \n\nPlease click the link below to finish setting up your account.\n\nhttp://<<domain>>.<<tld>>/verifyAccount/" + str(registrationKey) + "\n\nThe Dueto Team"

    message = """From: %s\nTo: %s\nSubject: %s\n\n%s
    """ % (FROM, ", ".join(TO), SUBJECT, TEXT)
    try:
        server = smtplib.SMTP("smtp.gmail.com", 587)
        server.ehlo()
        server.starttls()
        server.login(gmail_user, gmail_pwd)
        server.sendmail(FROM, TO, message)
        server.close()
        print "sent"
    except Exception, e:
    	print e
    	pass

# captchaSuccess = checkGReCaptchaResponse(args["g-recaptcha-response"], self.request.remote_ip, grecaptchaSecretKey)
def checkGReCaptchaResponse(clientResponse, remoteip, secret):
	data = {
		"secret": secret,
		"response": clientResponse,
		"remoteip": remoteip
	}
	r = requests.post("https://www.google.com/recaptcha/api/siteverify", data=data)
	return json.loads(r.text)["success"]

def convertRequestArgs(args):
	return json.loads(args)

if __name__ == "__main__":

	log.startLogging(sys.stdout)

	application = cyclone.web.Application([
		(r"/", MainHandler),
		
		(r"/u/(.*)", AccountHandler),

		(r"/login", LoginHandler),
		(r"/logout", LogoutHandler),
		(r"/register", RegistrationHandler),
		(r"/verifyAccount/(.*)", VerifyAccount),

		(r"/static/(.*)", cyclone.web.StaticFileHandler, {'path': os.path.join(root, 'static')})
	], cookie_secret=getCookieSecret(), debug=True)

	reactor.listenTCP(80, application)
	# reactor.listenSSL(443, application,
	# 	ssl.CertificateOptions(	privateKey=ssl_certificate_key,
	# 				certificate=ssl_certificate,
	# 				extraCertChain=ssl_certificate_chain))

	reactor.run()
