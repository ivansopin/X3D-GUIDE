X3D GUIDE
=============

## Abstract
Web3D is a family of interactive online three-dimensional (3D) graphics standards that includes such powerful technologies as Extensible 3D (X3D) modeling language. The X3D language has been widely adopted by professional organizations, researchers, and 3D designers worldwide. Despite the rich functionality, the language does not currently provide tools for rapid development of conventional graphical user interfaces (GUIs). An X3D author is responsible for building—from primitives—a purpose-specific set of required interface components, often for a single use. 
    In this thesis we address the challenge of creating consistent, efficient, interactive, and visually appealing GUIs by proposing the X3D Graphical User Interface Development Environment (X3D GUIDE) library. This library includes a wide range of cross-compatible X3D widgets, equipped with configurable appearance and behavior. With X3D GUIDE, we attempt to standardize the GUI construction across various X3D-driven projects, and improve the reusability, compatibility, adaptability, readability, and flexibility of many existing applications.

## Resources

### Demo
The supplied X3D GUIDE demo presents all major components of the library and a few respective configurations. The scene features Label, ControlButton, TextButton, TextToggleButton, RadioButton, CheckBox, ComboBox, Panel, TabPanel, TextField, HorizontalSlider, Frame, and TaskBar visual prototypes. Try resizing the windows to see how BoxLayout, BorderLayout, GridLayout, and FlowLayout prototypes work. Play with various controls to dynamically manage the scene.

### Documentation
The X3D GUIDE documentation is generated using an automated tool I built specifically for the library. This tool parses the prototype files; identifies the inheritance relationship; reuses XML comments from the code to provide group, prototype, attribute, and method descriptions; and links special tags to correct documentation sections. The documentation is not yet complete, but is a good starting point for some who is interested in the X3D GUIDE API.

### Source Code
X3D GUIDE is an open-source project, so you can download the source code to see what is happening behind the scenes. The one-file minified version is also available. The development is still in progress, so certain features might not be fully functional. If you find bugs or other problems with the code, or would like to contribute to this project, please email or skype me. The entire code will be fully commented soon.