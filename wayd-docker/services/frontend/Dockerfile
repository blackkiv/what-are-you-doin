FROM node:22 as build
WORKDIR /app
COPY  /wayd-frontend ./
RUN npm install
RUN npm run build

WORKDIR /
# download alpine filesystem
ENV ALPINE_VERSION=3.21.0
ADD https://dl-cdn.alpinelinux.org/alpine/latest-stable/releases/x86_64/alpine-minirootfs-$ALPINE_VERSION-x86_64.tar.gz /
RUN mkdir fs && tar -xzf alpine-minirootfs-$ALPINE_VERSION-x86_64.tar.gz -C /fs

FROM ghcr.io/umputun/reproxy
# copy alpine filesystem to current /
COPY --from=build fs/ /
# copy reproxy static files
COPY --from=build /app/dist /srv/site
COPY /wayd-docker/services/frontend/entrypoint.sh /srv/entrypoint.sh
COPY /wayd-docker/services/frontend/config.yml /srv/config.yml
RUN ls -l /srv
RUN chmod +x /srv/entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["/srv/entrypoint.sh"]
CMD ["/srv/reproxy", "--assets.location=/srv/site", "--file.enabled", "--file.name=/srv/config.yml", "--logger.enabled", "--max=50M", "--assets.spa", "--logger.stdout"]
