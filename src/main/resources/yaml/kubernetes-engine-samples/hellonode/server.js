/**
 * Copyright 2017 The Kubernetes Authors All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

const http = require('http');
const os = require('os');

const port = process.env.PORT || 8080;

process.on('SIGINT', function() {
  console.log('shutting down...');
  process.exit(1);
});

var handleRequest = function(request, response) {
  console.log(`Received request for URL: ${request.url}`);
  response.writeHead(200);
  response.end(`Hello, World!\nHostname: ${os.hostname()}\n`);
};

var www = http.createServer(handleRequest);
www.listen(port, () => {
  console.log(`server listening on port ${port}`);
});
