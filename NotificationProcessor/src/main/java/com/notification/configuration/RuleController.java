package com.notification.configuration;

import java.io.IOException;
import java.util.List;

import com.notification.dto.StepsConfig;
import com.notification.dto.UserRoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.dto.RuleDTO;
import com.notification.service.impl.RedisService;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

	@Autowired
	private RedisService redisService;

	@PostMapping("/add")
	public ResponseEntity<String> addRules(@RequestBody List<RuleDTO> ruleDTOList) throws IOException {
		for (RuleDTO ruleDTO : ruleDTOList) {
			if (ruleDTO.getKey() == null || ruleDTO.getValue() == null) {
				return ResponseEntity.badRequest().body("Key and Value must not be null");
			}
			redisService.saveRule(ruleDTO.getKey(), ruleDTO.getValue());
		}
		return ResponseEntity.ok("Rules added successfully");
	}

	@PostMapping("/addUserRole")
	public ResponseEntity<String> addRulesForUserRole(@RequestBody List<UserRoleDto> rolesDto) {
		for ( UserRoleDto ruleDTO : rolesDto) {
			if (ruleDTO.getRole() == null || ruleDTO.getRulesKey().isEmpty()) {
				return ResponseEntity.badRequest().body("User role and its rule keys must not be null");
			}
			redisService.saveUserRoleAndRulesKeys(ruleDTO.getRole(), ruleDTO.getRulesKey());
		}
		return ResponseEntity.ok("Role & its rule keys added successfully");
	}

    @PostMapping("/addSteps")
    public ResponseEntity<String> configureSteps(@RequestBody List<StepsConfig> pipelineStep) {
        for ( StepsConfig steps : pipelineStep) {
            if (steps.getRole() == null || steps.getSteps().isEmpty()) {
                return ResponseEntity.badRequest().body("Steps must not be null");
            }
            redisService.savePipelineSteps(steps.getRole(), steps.getSteps());
        }
        return ResponseEntity.ok("Role & pipeline steps added successfully");
    }
}