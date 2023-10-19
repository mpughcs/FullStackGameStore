# Use an official nginx image as a parent image
# so that we can run our application in a nginx environment
FROM node:18

WORKDIR /gampstore-frontend

# Copy the package.json and package-lock.json files
COPY package*.json ./

RUN npm install

# Copy the rest of the files
COPY . .

# Expose port 80 so that the container can be reached from outside
EXPOSE 3000

# run development server
CMD ["npm", "start"]

