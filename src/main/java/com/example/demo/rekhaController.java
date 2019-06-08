package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("ALL")
@Controller
public class rekhaController {

    @Autowired
    UserService userService;
    @Autowired
    ClosetRepository closetRepository;
    @Autowired
    TopRepository topRepository;

    @Autowired
    BottomRepository bottomRepository;

    @Autowired
    JacketRepository jacketRepository;

    @Autowired
    FootwearRepository footwearRepository;

    @Autowired
    AccessoriesRepository accessoriesRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listClosets(Model model) {
        User user = userService.getCurrentUser();
        // Gets the currently logged in user and maps it to "user" in the Thymeleaf template
        model.addAttribute("user", user );
        model.addAttribute("closets", closetRepository.findAllClosetsByUid(user.getId()));
        return "closetlist";
    }

    @GetMapping("/addcloset")
    public String closetForm(Model model) {

        User user = userService.getCurrentUser();
        Closet closet = new Closet();

        // Gets the currently logged in user and maps it to "user" in the Thymeleaf template
        model.addAttribute("user", user );
        model.addAttribute("closet", closet);
        return "closetform";
    }

    @PostMapping("/processcloset")
    public String processcloset(@Valid Closet closet, BindingResult result, Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        System.out.println("Debug 1>>>>>>>>>>>");
        if (result.hasErrors()) {
            model.addAttribute("users", userRepository.findAll());
            return "closetform";
        }

        closet.setUser(userService.getCurrentUser());
        closet.setUid(userService.getCurrentUser().getId());
        userService.getCurrentUser().setCloset(closet);

        closetRepository.save(closet);
        userRepository.save(userService.getCurrentUser());
        System.out.println("Debug 4>>>>>>>>>>>");
        return "redirect:/";
    }
    @GetMapping("/addtop")
    public String topForm(Model model) {

        User user = userService.getCurrentUser();
        long uid = userService.getCurrentUser().getId();
        Top top = new Top();


        // Gets the currently logged in user and maps it to "user" in the Thymeleaf template
        model.addAttribute("user", user );
        model.addAttribute("top", top);
        model.addAttribute("closets", closetRepository.findAllClosetsByUid(user.getId()));
        model.addAttribute("file", top.getImgUrl());
        return "topform";
    }

    @PostMapping("/processtop")
    public String processtop(@Valid Top top, BindingResult result, Model model,
                              @RequestParam("file")MultipartFile file) {
        model.addAttribute("user", userService.getCurrentUser());
        System.out.println("Debug 1>>>>>>>>>>>");
        if (result.hasErrors()) {
            model.addAttribute("users", userRepository.findAll());
            return "topform";
        }
        System.out.println("Debug 2>>>>>>>>>>>");
        if(!file.isEmpty()) {
            try {
                Map uploadResult = cloudc.upload(file.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));
                top.setImgUrl(uploadResult.get("url").toString());
                topRepository.save(top);
            }catch (IOException e) {
                e.printStackTrace();
                return "topform";
            }
        }
        System.out.println("Debug 3>>>>>>>>>>>");
        //top.setUid(userService.getCurrentUser().getId());
       // top.getCloset().setUser(userService.getCurrentUser());

        System.out.println("Debug 4>>>>>>>>>>>");
        return "redirect:/";
    }

    @GetMapping("/addbottom")
    public String bottomForm(Model model) {

        User user = userService.getCurrentUser();
        Bottom bottom = new Bottom();


        // Gets the currently logged in user and maps it to "user" in the Thymeleaf template
        model.addAttribute("user", user );
        model.addAttribute("bottom", bottom);
        model.addAttribute("closets", closetRepository.findAllClosetsByUid(user.getId()));
        model.addAttribute("file", bottom.getImgUrl());
        return "bottomform";
    }

    @PostMapping("/processbottom")
    public String processbottom(@Valid Bottom bottom, BindingResult result, Model model,
                             @RequestParam("file")MultipartFile file) {
        model.addAttribute("user", userService.getCurrentUser());
        System.out.println("Debug 1>>>>>>>>>>>");
        if (result.hasErrors()) {
            model.addAttribute("users", userRepository.findAll());
            return "bottomform";
        }
        System.out.println("Debug 2>>>>>>>>>>>");
        if(!file.isEmpty()) {
            try {
                Map uploadResult = cloudc.upload(file.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));
                bottom.setImgUrl(uploadResult.get("url").toString());
            }catch (IOException e) {
                e.printStackTrace();
                return "bottomform";
            }
        }
        System.out.println("Debug 3>>>>>>>>>>>");
        //top.setUid(userService.getCurrentUser().getId());
        // top.getCloset().setUser(userService.getCurrentUser());

        bottomRepository.save(bottom);
        System.out.println("Debug 4>>>>>>>>>>>");
        return "redirect:/";
    }

    @GetMapping("/addjacket")
    public String jacketForm(Model model) {

        User user = userService.getCurrentUser();
        Jacket jacket = new Jacket();


        // Gets the currently logged in user and maps it to "user" in the Thymeleaf template
        model.addAttribute("user", user );
        model.addAttribute("jacket", jacket);
        model.addAttribute("closets", closetRepository.findAllClosetsByUid(user.getId()));
        model.addAttribute("file", jacket.getImgUrl());
        return "jacketform";
    }

    @PostMapping("/processjacket")
    public String processjacket(@Valid Jacket jacket, BindingResult result, Model model,
                                @RequestParam("file")MultipartFile file) {

        System.out.println("Debug 1>>>>>>>>>>>");
        if (result.hasErrors()) {
            model.addAttribute("users", userRepository.findAll());
            return "jacketform";
        }
        System.out.println("Debug 2>>>>>>>>>>>");
        if(!file.isEmpty()) {
            try {
                Map uploadResult = cloudc.upload(file.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));
                jacket.setImgUrl(uploadResult.get("url").toString());
            }catch (IOException e) {
                e.printStackTrace();
                return "jacketform";
            }
        }
        System.out.println("Debug 3>>>>>>>>>>>");
        //top.setUid(userService.getCurrentUser().getId());
        // top.getCloset().setUser(userService.getCurrentUser());

        jacketRepository.save(jacket);
        System.out.println("Debug 4>>>>>>>>>>>");
        return "redirect:/";
    }

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("/addfootwear")
    public String footwearForm(Model model) {

        User user = userService.getCurrentUser();
        Footwear footwear = new Footwear();


        // Gets the currently logged in user and maps it to "user" in the Thymeleaf template
        model.addAttribute("user", user );
        model.addAttribute("footwear", footwear);
        model.addAttribute("closets", closetRepository.findAllClosetsByUid(user.getId()));
        model.addAttribute("file", footwear.getImgUrl());
        return "footwearform";
    }

    @PostMapping("/processfootwear")
    public String processFootwear(@Valid Footwear footwear, BindingResult result, Model model,
                                @RequestParam("file")MultipartFile file) {

        System.out.println("Debug 1>>>>>>>>>>>");
        if (result.hasErrors()) {
            model.addAttribute("users", userRepository.findAll());
            return "footwearform";
        }
        System.out.println("Debug 2>>>>>>>>>>>");
        if(!file.isEmpty()) {
            try {
                Map uploadResult = cloudc.upload(file.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));
                footwear.setImgUrl(uploadResult.get("url").toString());
            }catch (IOException e) {
                e.printStackTrace();
                return "footwearform";
            }
        }
        System.out.println("Debug 3>>>>>>>>>>>");
        //top.setUid(userService.getCurrentUser().getId());
        // top.getCloset().setUser(userService.getCurrentUser());

        footwearRepository.save(footwear);
        System.out.println("Debug 4>>>>>>>>>>>");
        return "redirect:/";
    }
    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("/addaccessory")
    public String accessoryForm(Model model) {

        User user = userService.getCurrentUser();
        Accessories accessory = new Accessories();


        // Gets the currently logged in user and maps it to "user" in the Thymeleaf template
        model.addAttribute("user", user );
        model.addAttribute("accessory", accessory);
        model.addAttribute("closets", closetRepository.findAllClosetsByUid(user.getId()));
        model.addAttribute("file", accessory.getImgUrl());
        return "accessoryform";
    }

    @PostMapping("/processaccessory")
    public String processAccessory(@Valid Accessories accessory, BindingResult result, Model model,
                                  @RequestParam("file")MultipartFile file) {

        System.out.println("Debug 1>>>>>>>>>>>");
        if (result.hasErrors()) {
            model.addAttribute("users", userRepository.findAll());
            return "accessoryform";
        }
        System.out.println("Debug 2>>>>>>>>>>>");
        if(!file.isEmpty()) {
            try {
                Map uploadResult = cloudc.upload(file.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));
                accessory.setImgUrl(uploadResult.get("url").toString());
            }catch (IOException e) {
                e.printStackTrace();
                return "accessoryform";
            }
        }
        System.out.println("Debug 3>>>>>>>>>>>");
        //top.setUid(userService.getCurrentUser().getId());
        // top.getCloset().setUser(userService.getCurrentUser());

        accessoriesRepository.save(accessory);
        System.out.println("Debug 4>>>>>>>>>>>");
        return "redirect:/";
    }



    @RequestMapping("/detail/{id}")
    public String showCloset(@PathVariable("id") long id, Model model) {
        User user = userService.getCurrentUser();
        // Gets the currently logged in user and maps it to "user" in the Thymeleaf template
        model.addAttribute("user", user );
        closetRepository.findById(id).ifPresent(o -> model.addAttribute("closet", o));
        // model.addAttribute("message", messageRepository.findById(id));
        Optional <Closet> closet = closetRepository.findById(id);
        model.addAttribute("tops", closetRepository.findById(id).get().getTops());
        model.addAttribute("jackets", closetRepository.findById(id).get().getJackets());
        model.addAttribute("bottoms", closetRepository.findById(id).get().getBottoms());
        model.addAttribute("footwears", closetRepository.findById(id).get().getFootwears());
        model.addAttribute("accessories", closetRepository.findById(id).get().getAccessories());

        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCloset(@PathVariable("id") long id, Model model) {
        User user = userService.getCurrentUser();
        // Gets the currently logged in user and maps it to "user" in the Thymeleaf template
        model.addAttribute("user", user);
        //model.addAttribute("message", messageRepository.findById(id));
        closetRepository.findById(id).ifPresent(o -> model.addAttribute("closet", o));
        Closet cls = closetRepository.findByIdAndUid(id, userService.getCurrentUser().getId());
        if (cls != null) {
            System.out.println("Found closet\n");
            cls.setUid(userService.getCurrentUser().getId());
            closetRepository.save(cls);
            return "closetform";
        } else {
            return "redirect:/listclosets";
        }
    }

    @RequestMapping("/delete/{id}")
    public String delCloset(@PathVariable("id") long id) {
        User user = userService.getCurrentUser();
        Closet cls = closetRepository.findByIdAndUid(id,userService.getCurrentUser().getId());
        if(cls != null || (roleRepository.findByRoleAndUsersId("ADMIN", user.getId()) != null)) {
            closetRepository.deleteById(id);
            System.out.println("Found closet\n");
        }
        return "redirect:/";
    }

    @GetMapping("/addpackinglist")
    public String packingForm(Model model) {
        User user = userService.getCurrentUser();
        // Gets the currently logged in user and maps it to "user" in the Thymeleaf template
        model.addAttribute("user", user );

        Closet closet = closetRepository.findByUidAndClosetName(user.getId(), "Packing closet");

        if(closet == null) {
            model.addAttribute("pcloset", new Closet());
        } else {
            System.out.println("Closet found id"+closet.getId()+closet.getClosetName());
            model.addAttribute("pcloset", closet);
        }
        model.addAttribute("closets", closetRepository.findAllClosetsByUid(user.getId()));

        return "packinglist";
    }

    @PostMapping("/processpackinglist")
    public String processPacking(@Valid @RequestParam("selectedcloset") Closet closet,
                                 @ModelAttribute("pcloset") Closet pcloset,
                                 Model model) {
        User user = userService.getCurrentUser();
        //top.setUid(userService.getCurrentUser().getId());
        // top.getCloset().setUser(userService.getCurrentUser());

        //Loop through all tops and check if selected
        System.out.println("The selected closet id "+closet.getId());
        pcloset.setClosetName("Packing closet");
        pcloset.setUid(user.getId());
        pcloset.setUser(user);

        //Check if this closet has already been added
        Closet closetExists = closetRepository.findByUidAndClosetName(user.getId(), "Packing closet");
        if(closetExists == null)
            user.setCloset(pcloset);
        userRepository.save(user);
       // closetRepository.save(pcloset);
        System.out.println("Debug 4>>>>>>>>>>>");
        model.addAttribute("user", user);
        model.addAttribute("pcloset", pcloset);
        model.addAttribute("selectedcloset", closet);
        return "packingitem";
    }

    @PostMapping("/processpackingitem")
    public String processPackingItem(@Valid @RequestParam("selectedtops") List<String> selectedtops,
                                     @Valid @RequestParam("selectedjackets") List<String> selectedjackets,
                                     @Valid @RequestParam("selectedbottoms") List<String> selectedbottoms,
                                     @Valid @RequestParam("selectedfootwear") List<String> selectedfootwear,
                                     @Valid @RequestParam("selectedaccessory") List<String> selectedaccessory,
                                     @ModelAttribute("pcloset") Closet pcloset, Model model) {
        User user = userService.getCurrentUser();
        Closet closet = closetRepository.findByUidAndClosetName(user.getId(), "Packing closet");

        if(selectedtops.size() >=2) {
            for (String s : selectedtops) {
                if(s.equalsIgnoreCase("checked")) continue;
                long topId = Long.parseLong(s);
                System.out.println("The top id is " + topId);
                //Add this to the packing list tops

                Top t = topRepository.findById(topId).get();


                if (t != null && closet != null) {
                    System.out.println("Adding top to pcloset");
                    t.setCloset(closet);
                    closet.setTops(t);
                    topRepository.save(t);
                }

            }
        }
        //Add the selected jackets
        if(selectedjackets.size() >=2) {
            for (String s : selectedjackets) {
                if(s.equalsIgnoreCase("checked")) continue;
                long jacketId = Long.parseLong(s);
                System.out.println("The top id is " + jacketId);
                //Add this to the packing list tops

                Jacket j = jacketRepository.findById(jacketId).get();


                if (j != null && closet != null) {
                    System.out.println("Adding jacket to pcloset");
                    j.setCloset(closet);
                    closet.setJacket(j);
                    jacketRepository.save(j);
                }

            }
        }

        //Add the selected bottoms
        if(selectedbottoms.size() >=2) {
            for (String s : selectedbottoms) {
                if(s.equalsIgnoreCase("checked")) continue;
                long bottomId = Long.parseLong(s);
                System.out.println("The bottom id is " + bottomId);
                //Add this to the packing list tops

                Bottom b = bottomRepository.findById(bottomId).get();


                if (b != null && closet != null) {
                    System.out.println("Adding bottom to pcloset");
                    b.setCloset(closet);
                    closet.setBottom(b);
                    bottomRepository.save(b);
                }

            }
        }



        //Add the selected footwear
        if(selectedfootwear.size() >=2) {
            for (String s : selectedfootwear) {
                if(s.equalsIgnoreCase("checked")) continue;
                long footwearId = Long.parseLong(s);
                System.out.println("The footwear id is " + footwearId);
                //Add this to the packing list tops

                Footwear f = footwearRepository.findById(footwearId).get();

                if (f != null && closet != null) {
                    System.out.println("Adding footwear to pcloset");
                    f.setCloset(closet);
                    closet.setFootwear(f);
                    footwearRepository.save(f);
                }

            }
        }

        //Add the selected accessory
        if(selectedaccessory.size() >=2) {
            for (String s : selectedaccessory) {
                if(s.equalsIgnoreCase("checked")) continue;
                long accessoryId = Long.parseLong(s);
                System.out.println("The accessory id is " + accessoryId);
                //Add this to the packing list tops

                Accessories a = accessoriesRepository.findById(accessoryId).get();

                if (a != null && closet != null) {
                    System.out.println("Adding footwear to pcloset");
                    a.setCloset(closet);
                    closet.setAccessory(a);
                    accessoriesRepository.save(a);
                }

            }
        }


        //closetRepository.save(pcloset);
        //closet.setUid(userService.getCurrentUser().getId());
        user.setCloset(closet);

        userRepository.save(userService.getCurrentUser());
        //closetRepository.save(closet);


        System.out.println("Debug 4>>>>>>>>>>>");
        model.addAttribute("user", user);
        model.addAttribute("pcloset", pcloset);
      //  model.addAttribute("selectedcloset", closet);
        return "redirect:/";
    }
}
