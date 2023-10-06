# Stage 1: Build Angular app
FROM node:16-alpine as builder

WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy app files
COPY . .

# Build the Angular app
RUN npm run build

# Stage 2: Setup Nginx and deploy built app
FROM nginx:latest

# Remove the default Nginx configuration file
RUN rm -rf /etc/nginx/conf.d/default.conf

# Copy custom Nginx configuration file
COPY nginx-local.conf /etc/nginx/conf.d/default.conf

RUN rm -rf /usr/share/nginx/html/*

# Copy the built app from the previous stage
COPY --from=builder /app/dist /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
