import pymongo

# Define the list of Dow Jones stock symbols
dow_symbols = [
    "AAPL",
    "MSFT",
    "GOOGL",
    "AMZN",
    "META",
    "JPM",
    "V",
    "JNJ",
    "WMT",
    "PG",
    "UNH",
    "HD",
    "T",
    "PYPL",
    "VZ",
    "CSCO",
    "XOM",
    "CVX",
    "INTC",
    "KO",
    "MRK",
    "PEP",
    "BAC",
    "CMCSA",
    "DIS",
    "IBM",
    "ORCL",
    "GS",
    "NKE",
    "CRM",
]

# MongoDB connection information
mongo_url = "mongodb://localhost:27017/"  # Update with your MongoDB URL
db_name = "portfolio-api-db"  # Update with your database name
collection_name = "trackedStock"  # Collection name

# Connect to MongoDB
client = pymongo.MongoClient(mongo_url)
db = client[db_name]
collection = db[collection_name]

# Insert Dow Jones symbols into the MongoDB collection
for symbol in dow_symbols:
    collection.insert_one({"symbol": symbol})

# Close the MongoDB connection
client.close()

print("Dow Jones symbols inserted into the MongoDB collection.")
