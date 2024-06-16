@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    @Autowired
    private ScreenService screenService;

    @GetMapping("/")
    public ResponseEntity<List<Screen>> getAllScreens() {
        List<Screen> screens = screenService.getAllScreens();
        return ResponseEntity.ok(screens);
    }

    @PostMapping("/")
    public ResponseEntity<Screen> createScreen(@RequestBody Screen screen) {
        Screen newScreen = screenService.createScreen(screen);
        return ResponseEntity.ok(newScreen);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screen> getScreenById(@PathVariable("id") Long id) {
        Screen screen = screenService.getScreenById(id);
        if (screen != null) {
            return ResponseEntity.ok(screen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Screen> updateScreen(@PathVariable("id") Long id, @RequestBody Screen screenDetails) {
        Screen updatedScreen = screenService.updateScreen(id, screenDetails);
        if (updatedScreen != null) {
            return ResponseEntity.ok(updatedScreen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScreen(@PathVariable("id") Long id) {
        boolean deleted = screenService.deleteScreen(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
