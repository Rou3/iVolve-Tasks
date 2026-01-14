FROM python:3.10-slim

WORKDIR /app

COPY . .

RUN pip install flask

# Environment variables (production)
ENV APP_MODE=production
ENV APP_REGION=canada-west

EXPOSE 8080

CMD ["python", "app.py"]

