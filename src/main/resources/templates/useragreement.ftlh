<!-- Modal -->
<div class="modal fade" role="dialog" id="useragreementmodal">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">User Agreement</h4>
            </div>

            <!-- Modal Body -->
            <div class="modal-body">
            ${useragreement}
            </div>

            <!-- Modal Footer -->
            <div class="modal-footer">
                <div class="col-xs-6 col-xs-offset-3">
                    <div class="col-xs-6">
                        <a href="/logout" class="center-block btn btn-default">
                            Cancel
                        </a>
                    </div>
                    <div class="col-xs-6">
                        <a href="/accept" class="center-block btn btn-success">Accept
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal End -->

<script>
    $('#useragreementmodal').modal('show');
</script>