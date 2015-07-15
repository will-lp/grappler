package org.github.willlp.grappler

class PatchFactory {
    
    String content
    
    private PatchFactory() {}
    
    static from(File file) {
        assert false, 'not implemented'
    }
    
    static from(String content) {
        new PatchFactory(content: content).parse()
    }
    
}
