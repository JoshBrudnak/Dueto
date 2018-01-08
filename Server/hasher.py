import base64, hashlib, os

def getDigest(password, salt=None):
	if not salt:
		salt = base64.b64encode(os.urandom(32))
	digest = hashlib.sha256(salt + password).hexdigest()
	for x in range(0, 100001):
		digest = hashlib.sha256(digest).hexdigest()
	return salt, digest

def isPassword(password, salt, digest):
	return getDigest(password, salt)[1] == digest
