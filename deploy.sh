# Deploy plain text handler
gcloud functions deploy text \
   --region asia-east2 \
   --runtime java11 \
   --trigger-http \
   --memory 256MB \
   --entry-point io.kraftsman.handlers.PlainTextHandler \
   --allow-unauthenticated

# Deploy json handler
gcloud functions deploy json \
   --region asia-east2 \
   --runtime java11 \
   --trigger-http \
   --memory 256MB \
   --entry-point io.kraftsman.handlers.JsonHandler \
   --allow-unauthenticated

# Deploy rss handler
gcloud functions deploy feed \
   --region asia-east2 \
   --runtime java11 \
   --trigger-http \
   --memory 256MB \
   --entry-point io.kraftsman.handlers.RssHandler \
   --allow-unauthenticated
