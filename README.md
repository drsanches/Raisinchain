New crypto currency system

OPTION request processing:

Some python code:

    def do_OPTIONS(self):
        self.send_response(200, "OK")
        self.send_header('Access-Control-Allow-Headers', 'content-type, Accept, X-Access-Token, X-Application-Name, X-Request-Sent-Time, Host, User-Agent, Accept-Encoding, Content-Type, X-Unity-Version, Content-Length')
        self.send_header('Content-Length', '0')
        self.send_header('Access-Control-Allow-Credentials', 'true')
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS')
        self.end_headers()
        return
