class MultipleCandidatesError(Exception):
    def __init__(self, num_candidates, target_name):
        self.num_candidates = num_candidates
        self.target_name = target_name

    def __str__(self):
        return "Found %d candidates when searching for host %s." % (self.num_candidates, self.target_name)

class FaultyHandshakeError(Exception):
    def __init__(self, packet_id):
        self.packet_id = packet_id

    def __str__(self):
        return "Faulty handshake received, received packet id %d instead." % (self.packet_id)