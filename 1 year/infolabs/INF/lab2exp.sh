#!/bin/bash
touch shared.txt
ls -la
chmod u=r,go= shared.txt
sudo chown  kobeleva shared.txt
sudo chown  lakhanskii shared.txt
sudo chown -R kobeleva ~/infolabs/INF1
