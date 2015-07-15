package org.captain.CallFilter;

class CallFilter {
    
    private CallService service;
    
    @Override
    public List<CallDTO> getCalls() {
        return filterCalls(service.listCalls());
    }
    
    @Override
    public List<CallDTO> filterAndConvertCalls(List<Call> calls) {
        List<CallDTO> filteredCalls = new ArrayList<>();
        for (Call call : calls) {
            if (isValid(call)) {
                filteredCalls.add( convertToDto(dto) );
            }
        }
        return filteredCalls;
    }
    
    boolean isValid(Call call) {
        return call.isHang() && call.isClosed();
    }
    
    CallDTO convertToDto(Call call) {
        CallDTO dto = new CallDTO();
        converted.id = call.id;
        converted.date = call.date;
        return dto;
    }
    
}
