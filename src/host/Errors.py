class MultipleCandidatesError(Exception):
    def __init__(self, num_candidates, target_name):
        self.num_candidates = num_candidates
        self.target_name = target_name

    def __str__(self):
        return "Found %d candidates when searching for host %s." % (self.num_candidates, self.target_name)

class CandidateNotFoundError(Exception):
    def __init__(self, target_name):
        self.target_name = target_name

    def __str__(self):
        return "No candidate with host name %s found." % (self.target_name)

class FailedToConnectError(Exception):
    def __init__(self, target_name):
        self.target_name = target_name

    def __str__(self):
        return "Failed to connect to host with name %s." % (self.target_name)