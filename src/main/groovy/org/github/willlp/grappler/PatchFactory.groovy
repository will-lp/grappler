package org.github.willlp.grappler

class PatchFactory {
    
    String content
    
    private PatchFactory() {}
    
    static from(File file) {
        from file.text
    }
    
    static from(String content) {
        new PatchParser().parse content
    }
    
}
