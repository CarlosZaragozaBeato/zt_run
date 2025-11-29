from typing import List

from fastapi import FastAPI, Query, HTTPException

app = FastAPI()

@app.get("/")
def read_root():
    return {"message": "FastAPI funcionando correctamente 🚀"}


# Pagination

fake_data = [{"id": 1, "name": f"Item {i}"} for i in range(0,5)]

@app.get("/items/", response_model=List[dict])
def get_items(skip: int=Query(0, description="Number of items to skip"),
              limit:int=Query(10, description="Number  of items to retrieve")):
    """
        Get items with pagination
    :param skip:
    :param limit:
    :return:
    """
    if skip < 0:
        raise HTTPException(status_code = 400, detail="Skip parameter must be non-negative")

    if limit <= 0:
        raise HTTPException(status_code=400, detail="Limit parameter must be positive")

    start_idx = skip
    end_idx = skip + limit

    items = fake_data[start_idx:end_idx]
    return items