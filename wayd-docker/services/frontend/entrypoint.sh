#!/bin/sh
echo "Replacing environment variables in JS files and configs..."
sed -i "s|<backend_url>|$BACKEND_URL|g" /srv/config.yml
find /srv/site -type f -name "*.js" -exec sed -i 's|VITE_BACKEND_URL_PLACEHOLDER|'"$VITE_BACKEND_URL"'|g' {} \;
# Pass execution to Main container process (set by RUN directive)
exec "$@"
