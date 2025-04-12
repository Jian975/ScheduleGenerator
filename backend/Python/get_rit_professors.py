from ratemyprof_api import RateMyProfApi
import json

def get_rit_professor_ids():
    rit_api = RateMyProfApi(807)
    professors = rit_api.get_professor_list()
    ids = [prof['tid'] for prof in professors]
    print(json.dumps(ids))  # Output as JSON for Java to parse

if __name__ == "__main__":
    get_rit_professor_ids()