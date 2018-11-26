FROM node as npm
WORKDIR /app 
COPY ./ ./
RUN npm install -g @angular/cli && \
    ng build --prod --output-path docs

FROM nginx
WORKDIR /usr/share/nginx/html
COPY --from=npm /app/docs .
CMD service nginx restart && tail -f /dev/null